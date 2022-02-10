import 'package:flutter/material.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';
import 'package:role_heroes/clients/api/exceptions/server_error.dart';
import 'package:role_heroes/components/main_snackbar.dart';
import 'package:role_heroes/components/pre_loader_widget.dart';
import 'package:role_heroes/constants.dart';
import 'package:role_heroes/controllers/user_hero.dart';
import 'package:role_heroes/modules/heroes/models/user_hero.dart';
import 'package:role_heroes/screens/hero_detail.dart';
import 'package:role_heroes/utils/builders/error_notification_builder.dart';
import 'package:role_heroes/utils/enum_helper.dart';
import 'package:role_heroes/widgets/pre_loader.dart';

enum ActionByUserHero { Delete }

class HeroList extends StatefulWidget {
  final int gameId;
  final IUserHeroController controller;

  HeroList({
    @required this.gameId,
    @required this.controller,
  });

  @override
  State<StatefulWidget> createState() {
    return _HeroListState();
  }
}

class _HeroListState extends State<HeroList> {
  final IErrorNotificationBuilder errorNotificationBuilder = ErrorNotificationBuilder();
  ActionByUserHero currentAction;

  onSelectAction(BuildContext context, ActionByUserHero action, int heroId) async {
    PreLoader.show(context);

    if (action == ActionByUserHero.Delete) {
      ScaffoldMessenger.of(context).clearSnackBars();

      widget.controller.delete(heroId)
        .then((value) {
          ScaffoldMessenger.of(context).showSnackBar(
            MainSnackBar(
              content: Text(
                AppLocalizations.of(context).hero_delete
              )
            )
          );

          setState(() {});
        })
        .catchError((error) {
          ScaffoldMessenger.of(context).showSnackBar(MainSnackBar(content: Text(
            AppLocalizations.of(context).service_error
          )));
        })
        .whenComplete(() => PreLoader.hide(context));
    }
  }

  Widget builderHeroMenu(BuildContext context, int heroId) {
    return PopupMenuButton<ActionByUserHero>(
      onSelected: (action) {
        onSelectAction(context, action, heroId);
      },
      itemBuilder: (BuildContext context) => ActionByUserHero.values
        .map((action) =>
          PopupMenuItem<ActionByUserHero>(
            value: action,
            child: Text(EnumHelper.getLabelEnum<ActionByUserHero>(action)),
          )
        ).toList(),
    );
  }

  @override
  build(BuildContext context) {
    return FutureBuilder(
      future: widget.controller.getList(widget.gameId),
      builder: (BuildContext context, AsyncSnapshot<List<UserHero>> snapshot) {
        Widget result = Center(
          child: PreLoaderWidget(),
        );

        if (snapshot.hasData && snapshot.data.length > 0) {
          return Container(
            padding: EdgeInsets.all(gDefaultPadding / 2),
            child: ListView.builder(
              itemCount: snapshot.data.length,
              itemBuilder: (BuildContext context, int index) {
                UserHero userHero = snapshot.data.elementAt(index);
                return InkWell(
                  key: ValueKey(userHero.id),
                  onTap: () {
                    Navigator.of(context).pushNamed(
                      HeroDetailScreen.routeName,
                      arguments: {'heroId': userHero.id},
                    );
                  },
                  child: Card(
                    child: Padding(
                      padding: const EdgeInsets.all(gDefaultPadding / 2),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Row(
                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                            children: [
                              Text(userHero.name),
                              builderHeroMenu(context, userHero.id),
                            ],
                          ),
                        ],
                      ),
                    ),
                  ),
                );
              },
            ),
          );
        } else if (snapshot.hasData && snapshot.data.length == 0 || snapshot.hasError) {
          result = Center(child: Text(AppLocalizations.of(context).empty_list));

          if (snapshot.hasError) {
            if (snapshot.error.runtimeType == ServerError) {
              ScaffoldMessenger.of(context).showSnackBar(
                ServerError.toSnackBar(snapshot.error)
              );
            } else {
              ScaffoldMessenger.of(context).showSnackBar(
                MainSnackBar(
                  content: Text(AppLocalizations.of(context).service_error),
                )
              );
            }
          }
        }

        return result;
      },
    );
  }
}
