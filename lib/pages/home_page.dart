import 'package:flutter/material.dart';
import 'package:posttest/dominio/user/result.dart';
import 'package:posttest/service/User/srv_cliente_pos.dart';
import 'package:posttest/service/User/srv_verify_user.dart';

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
  String txtUser = '';
  String txtPass = '';

  void exenameDeviceDP() async {
    final resul = PlaformChannel();
    final res = await resul.fingerChannelDP.nameDeviceDP();
    setState(() {
      nameDeviceDP = res as String;
    });
  }

  void _capturaFinger() async {
    final resul = PlaformChannel();
    final res = await resul.fingerChannelDP.capturaFinger();
    setState(() {
      fingertxt = res as String;
    });
  }

  void _autentica() async {
    Result vresul = await SrvClientePos.autentica("picapiedr4", "WilmaPic4");
    setState(() {
      fingertxt = vresul.verifyUserResult == null
          ? "error autentica"
          : vresul.verifyUserResult!.message ?? "mess error";
    });
  }

  void _envioHuella() async {
    final vresul =
        await SrvClientePos.envioHuella(pDI: "5961581", pPass: fingertxt);
    setState(() {
      fingertxt = vresul.verifyUserResult == null
          ? "error autentica"
          : vresul.verifyUserResult!.message ?? "mess error";
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: const Text("prueba de algo como sera"),
      ),
      body: SingleChildScrollView(
        child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceAround,
                children: [
                  const Text(
                    'Device:',
                  ),
                  Text(
                    nameDeviceDP,
                    style: Theme.of(context).textTheme.headlineSmall,
                    softWrap: true,
                  ),
                ],
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
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceAround,
                children: [
                  MaterialButton(
                    onPressed: _autentica,
                    color: Colors.blue,
                    child: const Text("testLogin"),
                  ),
                  MaterialButton(
                    onPressed: _capturaFinger,
                    color: Colors.blue,
                    child: const Text("finger"),
                  ),
                  MaterialButton(
                    onPressed: _envioHuella,
                    color: Colors.blue,
                    child: const Text("envioHuella"),
                  ),
                ],
              )
            ],
          ),
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
