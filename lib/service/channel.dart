import 'package:flutter/services.dart';

import 'finger_channel_dp.dart';

class PlaformChannel {
  static const methodChannelName = "com.prodem/mcDP";
  final MethodChannel methodChannel = const MethodChannel(methodChannelName);

  late FingerChannelDP fingerChannelDP;

  PlaformChannel() {
    fingerChannelDP = FingerChannelDP(methodChannel);
  }
}

class ChannelMethod {
  final MethodChannel methodChannel;
  ChannelMethod(this.methodChannel);
}
