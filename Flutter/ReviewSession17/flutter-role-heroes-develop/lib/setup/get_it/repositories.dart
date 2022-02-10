import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:get_it/get_it.dart';
import 'package:role_heroes/clients/api/client.dart';
import 'package:role_heroes/clients/api/role_heroes_client.dart';
import 'package:role_heroes/controllers/user_hero.dart';
import 'package:role_heroes/modules/games/repositories/repository.dart';
import 'package:role_heroes/modules/heroes/controller/user_hero.dart';
import 'package:role_heroes/modules/heroes/repositories/user_hero.dart';
import 'package:role_heroes/modules/heroes/repositories/user_hero_data.dart';
import 'package:role_heroes/modules/structural_attribute/repositories/repository.dart';
import 'package:role_heroes/repository/games.dart';
import 'package:role_heroes/repository/structural_attribute.dart';
import 'package:role_heroes/repository/user_heroes.dart';
import 'package:role_heroes/screens/hero_detail.dart';
import 'package:role_heroes/utils/builders/hero_detail_screen_builder.dart';
import 'package:role_heroes/utils/secure_storages.dart';

setupRepositories() {
  GetIt.I.registerFactory<IGamesRepository>(() => GamesRepository(
    apiClient: GetIt.I.get<Client>(),
    accessTokenStorage: GetIt.I.get<AccessTokenStorage>(),
  ));

  GetIt.I.registerFactory<IStructuralAttributeRepository>(() => StructuralAttributeRepository(
    apiClient: GetIt.I.get<Client>(),
    accessTokenStorage: GetIt.I.get<AccessTokenStorage>(),
  ));

  GetIt.I.registerFactory<IUserHeroesRepository>(() => UserHeroesRepository(
    apiClient: GetIt.I.get<Client>(),
    accessTokenStorage: GetIt.I.get<AccessTokenStorage>(),
  ));

  GetIt.I.registerFactory<IUserHeroDataRepository>(() => UserHeroDataRepository(
    apiClient: GetIt.I.get<Client>(),
    accessTokenStorage: GetIt.I.get<AccessTokenStorage>(),
  ));
}
