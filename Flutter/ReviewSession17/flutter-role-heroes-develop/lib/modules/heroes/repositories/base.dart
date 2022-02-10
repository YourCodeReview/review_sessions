import 'package:get_it/get_it.dart';
import 'package:role_heroes/clients/api/client.dart';
import 'package:role_heroes/clients/api/role_heroes_client.dart';
import 'package:role_heroes/utils/secure_storages.dart';

// TODO збавится от этого класса
abstract class BaseRepository {
  bool checkSuccessResponse(Map response) => !response.containsKey('errors');
}
