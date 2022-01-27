import 'package:flutter/material.dart';
import 'package:role_heroes/components/pre_loader_widget.dart';
import 'package:role_heroes/controllers/user_hero.dart';
import 'package:role_heroes/modules/heroes/models/user_hero.dart';
import 'package:role_heroes/utils/builders/hero_detail_screen_builder.dart';
import 'package:role_heroes/widgets/category_tab.dart';

class HeroDetailScreen extends StatefulWidget {
  static String routeName = '/hero';
  final IUserHeroController controller;
  final IHeroDetailScreenBuilder screenBuilder;

  HeroDetailScreen({
    Key key,
    @required this.controller,
    @required this.screenBuilder,
  });

  @override
  State<StatefulWidget> createState() => _HeroDetailScreenState();
}

class _HeroDetailScreenState extends State<HeroDetailScreen> {
  int heroId;

  @override
  Widget build(BuildContext context) {
    heroId = (ModalRoute.of(context).settings.arguments as Map<String, dynamic>)['heroId'] as int;
    return FutureBuilder(
      future: widget.controller.getDetail(heroId),
      builder: (context, AsyncSnapshot<UserHero> snapshot) {
        Widget result = Scaffold(
          body: Center(
            child: PreLoaderWidget(),
          ),
        );

        if (snapshot.hasData) {
          UserHero hero = snapshot.data;

          widget.screenBuilder.reset();
          widget.screenBuilder.build(context, hero, widget.controller);
          Map<CategoryTab, Widget> mapWidgets = widget.screenBuilder.getResult();

          List<CategoryTab> categories = mapWidgets.keys.toList();

          result = DefaultTabController(
            length: categories.length,
            child: Scaffold(
              appBar: AppBar(
                title: Text(hero.name),
                bottom: TabBar(tabs: categories, isScrollable: true),
              ),
              body: TabBarView(
                children: mapWidgets.values.toList(),
              ),
            ),
          );
        } else if (snapshot.hasError) {
          result = Scaffold(
            body: Center(
              child: Text(snapshot.error.toString()),
            ),
          );
        }

        return result;
      },
    );
  }
}
