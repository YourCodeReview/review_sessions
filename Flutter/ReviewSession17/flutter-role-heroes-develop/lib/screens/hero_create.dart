import 'package:flutter/material.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';
import 'package:role_heroes/components/hero_create.dart';
import 'package:role_heroes/controllers/user_hero.dart';

class HeroCreateScreen extends StatelessWidget {
  static String routeName = '/heroes/create';

  final IUserHeroController controller;

  HeroCreateScreen({
    Key key,
    @required this.controller,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(AppLocalizations.of(context).hero_create),
      ),
      body: HeroCreateForm(
        controller: controller,
      ),
    );
  }
}
