import 'package:role_heroes/modules/games/models/game.dart';

abstract class IGameController {
  Future<List<Game>> games();
}
