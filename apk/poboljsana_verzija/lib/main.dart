import 'package:draggable_scrollbar/draggable_scrollbar.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'dart:io';
import 'dart:typed_data';
import 'package:flutter/cupertino.dart';
import 'dart:async';
import 'dart:convert';
import 'package:flutter/services.dart';
import 'package:path_provider/path_provider.dart';
import 'package:dio/dio.dart';

int counter = 0;

void main() {
  runApp(DraggableScrollBarDemo(
  ));
}

class DraggableScrollBarDemo extends StatelessWidget {

  const DraggableScrollBarDemo({
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      darkTheme: ThemeData(brightness: Brightness.dark),
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {

  MyHomePage({
    Key? key,
  }) : super(key: key);


  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  ScrollController _semicircleController = ScrollController();
  DateTime timebackpressed = DateTime.now();


  @override
  Widget build(BuildContext context) {

    return WillPopScope(
        onWillPop: () async {
          final diff = DateTime.now().difference(timebackpressed);
          final isExitWar = diff >= Duration(milliseconds: 1500);
          timebackpressed = DateTime.now();
          if (isExitWar) {
            return false;
          } else
            return true;
        },
    child: DefaultTabController(
      length: 1,
      child: Scaffold(
        appBar: AppBar(
        title: Center(child: Text("Photo studio Still")),
        ),
       //  body: Scaffold(
       //
       //    bottomNavigationBar: BottomAppBar(
       //        child: Text("Slike nastale u $pic_time h",
       //          style: TextStyle(fontSize: 20,fontWeight: FontWeight.bold,),
       //          textAlign: TextAlign.center,
       //        ),
       //
       // ),
            body: TabBarView(children: [
          SemicircleDemo(controller: _semicircleController),

        ]),
      ),)
    );
  }
}


class SemicircleDemo extends StatefulWidget {
  final ScrollController controller;
  const SemicircleDemo({
    Key? key,
    required this.controller,
  }) : super(key: key);

  @override
  SemicircleDemoState createState() => SemicircleDemoState(controller);
}

class SemicircleDemoState extends State<SemicircleDemo> {
  ScrollController controller=ScrollController();

  SemicircleDemoState(ScrollController c) {
    this.controller=c;
  }


    ///dobijanje i obrada slike
  String data = "";
  String time="";
  String url2 = "http://192.168.1.10:6969/serv/folderLen";
  int max = 0;
  List<Image> images = [];
  List<String> times = [];

  void imagemaker() async {

    var pathKreiranog = await _createFileFromString(data);
    setState(() {
      // var pathKreiranog = imaLiFajla().toString();
      // bool br = File(pathKreiranog).exists() as bool;
      // if(!br) {
      //   print("Citao sam iz ws");
      //
      String s = "$pathKreiranog";
      // }
      times.add(time);
      images.add(Image.file(
        File(s),
      ));

    });

    return null;
  }

  Future<String> _createFileFromString(String data) async {
    final encodedStr = data;
    Uint8List bytes = base64.decode(encodedStr);
    String dir = (await getApplicationDocumentsDirectory()).path;
    File file = File("$dir/$counter.jpg");
    await file.writeAsBytes(bytes);
    return file.path;
  }


  Future<String> getData() async {

    String url1 = "http://192.168.1.10:6969/serv/gemmeThisSlika/${counter}";
    int tmp = 0;
    var res = await Dio()
        .get(url2);
    setState(() {
      var resBody = res.data;
      tmp = resBody;
    });
    if (tmp > counter) {
    do{
      var res = await Dio()
          .get(url1);
      setState(() {
        var resBody = res.data;
        data = resBody['fajl'];
        max = resBody['mod'];
        time=resBody['time'];
      });}
      while(max!=counter);
      setState(() {
        imagemaker();
      });
      setState(() {
        counter++;
      });
    }
    return "Success!";
  }



  ///odje je kraj

  @override
  Widget build(BuildContext context) {
    return DraggableScrollbar.semicircle(
      labelTextBuilder:

          (offset) {
        final int currentItem = controller.hasClients
            ? (controller.offset / controller.position.maxScrollExtent * (images.length-1)).floor()
            : 0;
        return Text((() {
          if(true){
            if(times[currentItem]!=null){
            // for(int i=0;i<images.length;i++)
            // print(times[i]);
            return times[currentItem].toString()+"h";}

          }

          return "--";

        })(),style: TextStyle(
        color: Colors.black,), key: Key(times[currentItem].toString()+"h"),);
      },
     // backgroundColor: Colors.red,
      labelConstraints: BoxConstraints.tightFor(width: 80.0, height: 30.0),
      controller: controller,
      child: GridView.builder(
        controller: controller,


          padding: EdgeInsets.all(10),
          gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
            crossAxisSpacing: 0,
            mainAxisSpacing: 0,
            crossAxisCount: 3,
          ),
          itemCount: images.length,
          itemBuilder: (context, index) {
            return Stack(
              children: <Widget>[
                Center(child: Card(child:images[index],
                )),

              ],
              alignment: Alignment.center,
            );
          }

      ),
    );
  }



  @override
  void initState() {
    SystemChrome.setEnabledSystemUIMode(SystemUiMode.leanBack);
    super.initState();
    this.getData();
    Timer _everySecond = Timer.periodic(Duration(milliseconds: 1000), (Timer t) {
      this.getData();
    });



  }

}

