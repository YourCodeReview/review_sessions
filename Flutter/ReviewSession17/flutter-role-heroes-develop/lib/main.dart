import 'package:flutter/material.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:get_it/get_it.dart';
import 'package:role_heroes/screens/game_list.dart';
import 'package:role_heroes/screens/hero_change_values.dart';
import 'package:role_heroes/screens/hero_create.dart';
import 'package:role_heroes/screens/hero_detail.dart';
import 'package:role_heroes/screens/hero_list.dart';
import 'package:role_heroes/screens/login.dart';
import 'package:role_heroes/screens/register.dart';
import 'package:role_heroes/screens/splash.dart';
import 'package:role_heroes/setup/get_it/setup.dart';
import 'package:role_heroes/themes/light_theme.dart';

void main() {
  setup();

  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Role heroes',
      theme: LightTheme.build(context),
      localizationsDelegates: [
        AppLocalizations.delegate,
        GlobalMaterialLocalizations.delegate,
        GlobalWidgetsLocalizations.delegate,
        GlobalCupertinoLocalizations.delegate,
      ],
      supportedLocales: [
        Locale('ru', ''),
      ],
      routes: {
        SplashScreen.routeName: (context) => GetIt.I.get<SplashScreen>(),
        LoginScreen.routeName: (context) => GetIt.I.get<LoginScreen>(),
        RegisterScreen.routeName: (context) => GetIt.I.get<RegisterScreen>(),
        GameScreen.routeName: (context) => GetIt.I.get<GameScreen>(),
        HeroListScreen.routeName: (context) => GetIt.I.get<HeroListScreen>(),
        HeroDetailScreen.routeName: (context) => GetIt.I.get<HeroDetailScreen>(),
        HeroCreateScreen.routeName: (context) => GetIt.I.get<HeroCreateScreen>(),
        HeroChangeStructuralValues.routeName: (context) => GetIt.I.get<HeroChangeStructuralValues>(),
      },
    );
  }
}
