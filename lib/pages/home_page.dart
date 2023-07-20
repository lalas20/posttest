import 'package:flutter/material.dart';

import '../service/channel.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key, required this.title});
  final String title;

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  String nameDeviceDP = '';
  String fingertxt = '';
  bool tieneimg = false;

  void exenameDeviceDP() async {
    final resul = PlaformChannel();
    final res = await resul.fingerChannelDP.nameDeviceDP();
    setState(() {
      nameDeviceDP = res as String;
    });
  }

  _capturaFinger() async {
    final resul = PlaformChannel();
    final res = await resul.fingerChannelDP.capturaFinger();
    setState(() {
      fingertxt = res as String;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: const Text("prueba de algo como sera"),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            const Text(
              'Device:',
            ),
            Text(
              nameDeviceDP,
              style: Theme.of(context).textTheme.headlineMedium,
            ),
            tieneimg
                ? Image.asset(
                    "assets/img/finger.png",
                    width: 300,
                    height: 300,
                    fit: BoxFit.fill,
                  )
                : Image.asset(
                    "assets/img/finger.png",
                    width: 300,
                    height: 300,
                    fit: BoxFit.fill,
                  ),
            Text(
              fingertxt,
              style: Theme.of(context).textTheme.headlineMedium,
            ),
            MaterialButton(
              onPressed: _capturaFinger,
              color: Colors.blue,
              child: const Text("finger"),
            )
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: exenameDeviceDP,
        tooltip: 'Increment',
        child: const Icon(Icons.add),
      ),
    );
  }
}
