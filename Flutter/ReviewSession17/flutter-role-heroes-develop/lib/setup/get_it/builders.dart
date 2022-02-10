import 'package:get_it/get_it.dart';
import 'package:role_heroes/utils/builders/hero_detail_screen_builder.dart';

setupBuilders() {
  GetIt.I.registerFactory<IHeroDetailScreenBuilder>(() => HeroDetailScreenBuilder());
}
