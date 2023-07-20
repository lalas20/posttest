import 'package:flutter/services.dart';

import 'channel.dart';

class FingerChannelDP extends ChannelMethod {
  static const captureNameDevice = "captureNameDevice";
  static const captureFinger = "captureFinger";

  FingerChannelDP(MethodChannel methodChannel) : super(methodChannel);

  Future<String?> nameDeviceDP() async {
    try {
      final vRespuesta =
          await methodChannel.invokeMethod<String?>(captureNameDevice);
      // ignore: avoid_print
      print({"exito  $vRespuesta"});
      return vRespuesta;
    } catch (e) {
      // ignore: avoid_print
      print({"error  $e"});
    }
    return '';
  }

  Future<String?> capturaFinger() async {
    try {
      final vRespuesta =
          await methodChannel.invokeMethod<String?>(captureFinger);
      // ignore: avoid_print
      print({"exito  $vRespuesta"});
      return vRespuesta;
    } catch (e) {
      // ignore: avoid_print
      print({"error  $e"});
    }
    return '';
  }
}
