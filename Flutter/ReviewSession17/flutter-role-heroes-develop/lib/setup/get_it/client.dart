import 'package:dio/dio.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:get_it/get_it.dart';
import 'package:role_heroes/clients/api/client.dart';
import 'package:role_heroes/clients/api/role_heroes_client.dart';
import 'package:role_heroes/controllers/user_hero.dart';
import 'package:role_heroes/modules/heroes/controller/user_hero.dart';
import 'package:role_heroes/screens/hero_detail.dart';
import 'package:role_heroes/utils/builders/hero_detail_screen_builder.dart';
import 'package:role_heroes/utils/secure_storages.dart';

setupClient() {
  GetIt.I.registerSingleton<FlutterSecureStorage>(FlutterSecureStorage());
  GetIt.I.registerSingleton<AccessTokenStorage>(AccessTokenStorage());

  GetIt.I.registerFactory<Dio>(() {
    final BaseOptions options = BaseOptions(
      baseUrl: 'http://test-role-heroes.herokuapp.com/api',
    );
    return Dio(options);
  });
  GetIt.I.registerFactory<Client>(() {
    return new RoleHeroesClient(dio: GetIt.I.get<Dio>(), accessTokenStorage: GetIt.instance<AccessTokenStorage>());
  });
}
