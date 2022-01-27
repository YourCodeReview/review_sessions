import 'package:flutter/material.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';
import 'package:role_heroes/components/hero_list.dart';
import 'package:role_heroes/controllers/user_hero.dart';
import 'package:role_heroes/screens/hero_create.dart';

class HeroListScreen extends StatefulWidget {
  static String routeName = '/heroes';
  final IUserHeroController controller;

  HeroListScreen({
    Key key,
    @required this.controller,
  }) : super(key: key);

  @override
  State<StatefulWidget> createState() => _HeroListScreenState();
}

class _HeroListScreenState extends State<HeroListScreen> {
  @override
  Widget build(BuildContext context) {
    final int gameId = (ModalRoute.of(context).settings.arguments as Map<String, dynamic>)['gameId'] as int;
    return Scaffold(
      appBar: AppBar(
        title: Text(AppLocalizations.of(context).heroes),
      ),
      body: HeroList(gameId: gameId, controller: widget.controller),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          Navigator.of(context).pushNamed(
            HeroCreateScreen.routeName,
            arguments: <String, dynamic>{'gameId': gameId},
          ).then((value) => setState(() {}));
        },
        child: Icon(Icons.add),
      ),
    );
  }
}
