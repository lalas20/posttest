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

  void exenameDeviceDP() async {
    final resul = PlaformChannel();
    final res = await resul.fingerChannelDP.nameDeviceDP();
    setState(() {
      nameDeviceDP = res as String;
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
