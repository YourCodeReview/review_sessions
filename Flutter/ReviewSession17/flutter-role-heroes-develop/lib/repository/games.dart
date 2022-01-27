import 'package:role_heroes/modules/games/models/game.dart';

abstract class IGamesRepository {
  Future<List<Game>> getList();
}
