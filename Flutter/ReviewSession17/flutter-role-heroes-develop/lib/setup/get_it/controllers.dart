import 'package:get_it/get_it.dart';
import 'package:role_heroes/clients/api/client.dart';
import 'package:role_heroes/controllers/auth.dart';
import 'package:role_heroes/controllers/game.dart';
import 'package:role_heroes/controllers/structural_attribute.dart';
import 'package:role_heroes/controllers/user_hero.dart';
import 'package:role_heroes/modules/games/controllers/controller.dart';
import 'package:role_heroes/modules/heroes/controller/user_hero.dart';
import 'package:role_heroes/modules/structural_attribute/controllers/controller.dart';
import 'package:role_heroes/repository/games.dart';
import 'package:role_heroes/repository/structural_attribute.dart';
import 'package:role_heroes/repository/user_heroes.dart';
import 'package:role_heroes/utils/secure_storages.dart';

setupControllers() {
  GetIt.I.registerFactory<IUserHeroController>(() => UserHeroController(
    gamesRepository: GetIt.I.get<IGamesRepository>(),
    userHeroRepository: GetIt.I.get<IUserHeroesRepository>(),
    userHeroDataRepository: GetIt.I.get<IUserHeroDataRepository>(),
  ));

  GetIt.I.registerFactory<IGameController>(() => GameController(
    repository: GetIt.I.get<IGamesRepository>(),
  ));

  GetIt.I.registerFactory<IStructuralAttributeController>(() => StructuralAttributeController(
    repository: GetIt.I.get<IStructuralAttributeRepository>(),
  ));

  GetIt.I.registerFactory<IAuthController>(() => AuthController(
    apiClient: GetIt.I.get<Client>(),
    accessTokenStorage: GetIt.I.get<AccessTokenStorage>(),
  ));
}
