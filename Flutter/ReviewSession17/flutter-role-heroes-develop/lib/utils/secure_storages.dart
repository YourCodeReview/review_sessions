import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:get_it/get_it.dart';

abstract class BaseSecureStorage {
  final FlutterSecureStorage storage = GetIt.instance<FlutterSecureStorage>();
  String key;

  Future<String> getValue() async {
    return await storage.read(key: key);
  }

  void setValue(value) async {
    await storage.write(key: key, value: value);
  }

  void removeValue() async {
    await storage.read(key: key);
  }
}

class AccessTokenStorage extends BaseSecureStorage {
  String key = 'access_token';
}
