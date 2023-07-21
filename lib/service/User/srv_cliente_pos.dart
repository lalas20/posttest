import 'dart:convert';

import '../../dominio/user/aditional_item.dart';
import '../../dominio/user/credential.dart';
import '../../dominio/user/credential_verify_user.dart';
import '../../dominio/user/resul_get_user_session_info.dart';
import '../../dominio/user/result.dart';
import '../../dominio/user/verify_user_result.dart';
import '../../helper/util_preferences.dart';
import '../helper/util_conextion.dart';

class SrvClientePos {
  static Future<ResulGetUserSessionInfo> getUserSessionInfo(
      String pIdWebClient) async {
    ResulGetUserSessionInfo respuesta = ResulGetUserSessionInfo();
    GetUserSessionInfoResult getUserSessionInfo = GetUserSessionInfoResult();
    dynamic jsonResponse;
    try {
      final vPing = await UtilConextion.internetConnectivity();
      if (vPing == false) {
        getUserSessionInfo.codeBase = UtilConextion.errorInternet;
        getUserSessionInfo.state = 3;
        getUserSessionInfo.message = "No tiene acceso a internet";
        respuesta.getUserSessionInfoResult = getUserSessionInfo;
        return respuesta;
      }

      Map<String, String> vWebCliente = {
        'vIdWebClient': UtilPreferences.getIdWebPersonClient()
      };

      String vJSON = jsonEncode(vWebCliente);
      final response =
          await UtilConextion.httpPost(UtilConextion.getUserSessionInfo, vJSON);

      if (response.statusCode == 200) {
        jsonResponse = json.decode(response.body);
        respuesta = ResulGetUserSessionInfo.fromJson(jsonResponse);
      } else {
        respuesta = respuesta.errorRespuesta(response.statusCode);
      }
    } catch (e) {
      respuesta.getUserSessionInfoResult = GetUserSessionInfoResult();
      respuesta.getUserSessionInfoResult?.message =
          "error sub: ${e.toString()}";
      respuesta.getUserSessionInfoResult?.state = 3;
    }
    return respuesta;
  }

  static Future<Result> envioHuella(
      {required String pDI, required String pPass}) async {
    Result respuesta = Result();
    respuesta.verifyUserResult = VerifyUserResult();
    VerifyUserResult vObjVerify = VerifyUserResult();
    dynamic jsonResponse;
    try {
      final vPing = await UtilConextion.internetConnectivity();
      if (vPing == false) {
        vObjVerify.codeBase = UtilConextion.errorInternet;
        vObjVerify.state = 3;
        vObjVerify.message = "No tiene acceso a internet";
        respuesta.verifyUserResult = vObjVerify;
        return respuesta;
      }

      final vCredencialVeryUser =
          Credential(user: pDI, password: pPass, channel: 3, aditionalItems: [
        AditionalItems(key: 'IdATM', value: '1'),
        AditionalItems(key: 'TypeAuthentication', value: 'IdentityCard'),
      ]);
      final vRes = CredentialVerifyUser(credential: vCredencialVeryUser);

      String vJSON = jsonEncode(vRes.toJson());
      final response =
          await UtilConextion.httpPostSin(UtilConextion.verifyUser, vJSON);

      if (response.statusCode == 200) {
        jsonResponse = json.decode(response.body);
        respuesta = Result.fromJson(jsonResponse);

/* add sharepreference */
        AditionalItems? iTems = respuesta
            .verifyUserResult!.object!.aditionalItems!
            .firstWhere((element) => element.key == 'IdUsuario');
        UtilPreferences.setIdUsuario(iTems.value!);
        iTems = respuesta.verifyUserResult!.object!.aditionalItems!
            .firstWhere((element) => element.key == 'IdWebPersonClient');
        UtilPreferences.setIdWebPersonClient(iTems.value!);

        UtilPreferences.setToken(respuesta.verifyUserResult!.object!.token!);
        UtilPreferences.setUser(respuesta.verifyUserResult!.object!.user!);
/* fin de sharepreference */
      } else {
        respuesta = respuesta.errorRespuesta(response.statusCode);
      }
    } catch (e) {
      respuesta.verifyUserResult = VerifyUserResult();
      respuesta.verifyUserResult?.message = "error sub: ${e.toString()}";
      respuesta.verifyUserResult?.state = 3;
    }
    return respuesta;
  }

  static Future<Result> autentica(pDI, pPass) async {
    Result respuesta = Result();
    respuesta.verifyUserResult = VerifyUserResult();
    VerifyUserResult vObjVerify = VerifyUserResult();
    dynamic jsonResponse;
    try {
      final vPing = await UtilConextion.internetConnectivity();
      if (vPing == false) {
        vObjVerify.codeBase = UtilConextion.errorInternet;
        vObjVerify.state = 3;
        vObjVerify.message = "No tiene acceso a internet";
        respuesta.verifyUserResult = vObjVerify;
        return respuesta;
      }

      final vCredencialVeryUser = Credential(
          user: pDI,
          password: pPass,
          channel: 1,
          aditionalItems: [
            AditionalItems(key: 'IP', value: '255.255.255.255')
          ]);
      final vRes = CredentialVerifyUser(credential: vCredencialVeryUser);

      String vJSON = jsonEncode(vRes.toJson());
      final response =
          await UtilConextion.httpPostSin(UtilConextion.verifyUser, vJSON);

      if (response.statusCode == 200) {
        jsonResponse = json.decode(response.body);
        respuesta = Result.fromJson(jsonResponse);

/* add sharepreference */
        AditionalItems? iTems = respuesta
            .verifyUserResult!.object!.aditionalItems!
            .firstWhere((element) => element.key == 'IdUsuario');
        UtilPreferences.setIdUsuario(iTems.value!);
        iTems = respuesta.verifyUserResult!.object!.aditionalItems!
            .firstWhere((element) => element.key == 'IdWebPersonClient');
        UtilPreferences.setIdWebPersonClient(iTems.value!);

        UtilPreferences.setToken(respuesta.verifyUserResult!.object!.token!);
        UtilPreferences.setUser(respuesta.verifyUserResult!.object!.user!);
/* fin de sharepreference */
      } else {
        respuesta = respuesta.errorRespuesta(response.statusCode);
      }
    } catch (e) {
      respuesta.verifyUserResult = VerifyUserResult();
      respuesta.verifyUserResult?.message = "error sub: ${e.toString()}";
      respuesta.verifyUserResult?.state = 3;
    }
    return respuesta;
  }
}
