import 'package:get_it/get_it.dart';
import 'package:role_heroes/controllers/auth.dart';
import 'package:role_heroes/controllers/game.dart';
import 'package:role_heroes/controllers/structural_attribute.dart';
import 'package:role_heroes/controllers/user_hero.dart';
import 'package:role_heroes/screens/game_list.dart';
import 'package:role_heroes/screens/hero_change_values.dart';
import 'package:role_heroes/screens/hero_create.dart';
import 'package:role_heroes/screens/hero_detail.dart';
import 'package:role_heroes/screens/hero_list.dart';
import 'package:role_heroes/screens/login.dart';
import 'package:role_heroes/screens/register.dart';
import 'package:role_heroes/screens/splash.dart';
import 'package:role_heroes/utils/builders/hero_detail_screen_builder.dart';

setupScreens() {
  GetIt.I.registerFactory<SplashScreen>(() => SplashScreen(
      controller: GetIt.I.get<IAuthController>(),
  ));

  GetIt.I.registerFactory<LoginScreen>(() => LoginScreen(
    controller: GetIt.I.get<IAuthController>(),
  ));

  GetIt.I.registerFactory<RegisterScreen>(() => RegisterScreen(
    controller: GetIt.I.get<IAuthController>(),
  ));

  GetIt.I.registerFactory<GameScreen>(() => GameScreen(
    authController: GetIt.I.get<IAuthController>(),
    gameController: GetIt.I.get<IGameController>(),
  ));

  GetIt.I.registerFactory<HeroListScreen>(() => HeroListScreen(
    controller: GetIt.I.get<IUserHeroController>(),
  ));

  GetIt.I.registerFactory<HeroDetailScreen>(() => HeroDetailScreen(
      controller: GetIt.I.get<IUserHeroController>(),
      screenBuilder: GetIt.I.get<IHeroDetailScreenBuilder>()
  ));

  GetIt.I.registerFactory<HeroCreateScreen>(() => HeroCreateScreen(
    controller: GetIt.I.get<IUserHeroController>(),
  ));

  GetIt.I.registerFactory<HeroChangeStructuralValues>(() => HeroChangeStructuralValues(
    controller: GetIt.I.get<IStructuralAttributeController>(),
  ));
}
