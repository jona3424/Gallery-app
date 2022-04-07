import 'dart:io';
import 'dart:typed_data';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'dart:async';
import 'dart:convert';
import 'package:flutter/services.dart';
import 'package:path_provider/path_provider.dart';
import 'package:dio/dio.dart';

int counter = 0;

void main() {
  runApp(MaterialApp(
    home: Galerija(),
  ));
}

class Galerija extends StatefulWidget {
  @override
  GalerijaState createState() => GalerijaState();
}

class GalerijaState extends State<Galerija> {
  DateTime timebackpressed = DateTime.now();
  String data = "";
  String url2 = "http://192.168.31.2:6969/serv/folderLen";
  int max = 0;
  List<Image> images = [];

  Future<String> getData() async {
    String url1 = "http://192.168.31.2:6969/serv/gemmeThisSlika/${counter}";
    int tmp = 0;
    var res = await Dio()
        .get(url2);
    setState(() {
      var resBody = res.data;
      print(resBody);
      tmp = resBody;
    });
    if (tmp > counter) {

        var res = await Dio()
            .get(url1);
        setState(() {
          var resBody = res.data;
          data = resBody['fajl'];
          max = resBody['mod'];
          max--;
          imagemaker();
        });

       setState(() {
        counter++;
      });
    }
    return "Success!";
  }

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
        child: MaterialApp(
            darkTheme: ThemeData(brightness: Brightness.dark),
            home: Scaffold(
                appBar: AppBar(
                  title: Text("Photo studio Stil"),
                ),
                body: GridView.builder(
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
                        Text(
                          index.toString(),
                          style: TextStyle(
                              color: Colors.white.withOpacity(0.80),
                              fontSize: 30,
                              fontWeight: FontWeight.bold),
                          softWrap: true,
                        ),
                      ],
                      alignment: Alignment.center,
                    );
                 }

                ))));
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
