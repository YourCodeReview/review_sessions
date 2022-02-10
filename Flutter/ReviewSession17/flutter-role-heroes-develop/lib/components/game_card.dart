import 'package:flutter/material.dart';
import 'package:role_heroes/constants.dart';
import 'package:role_heroes/modules/games/models/game.dart';
import 'package:role_heroes/screens/hero_list.dart';

class GameCard extends StatelessWidget {
  Game game;

  GameCard({@required this.game});

  showDescription(BuildContext context, String description) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          content: Text(description),
          actions: [
            TextButton(
                onPressed: () { Navigator.pop(context); },
                child: Text('Close')),
          ],
        );
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Card(
      key: ValueKey(game.id),
      child: InkWell(
        onTap: () { Navigator.of(context).pushNamed(HeroListScreen.routeName, arguments: {'gameId': game.id}); },
        onLongPress: () {
          showDescription(context, game.description);
        },
        child: Container(
          height: 60,
          alignment: AlignmentDirectional.bottomStart,
          child: Center(
            child: Padding(
              padding: EdgeInsets.symmetric(horizontal: gDefaultPadding),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Text(game.name),
                  Icon(Icons.arrow_forward_ios_sharp, size: 15.0,),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}
