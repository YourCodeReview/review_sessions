import 'package:meta/meta.dart';
import 'package:role_heroes/controllers/game.dart';
import 'package:role_heroes/modules/games/models/game.dart';
import 'package:role_heroes/repository/games.dart';

class GameController implements IGameController {
  final IGamesRepository repository;

  GameController({
    @required this.repository,
  });

  @override
  Future<List<Game>> games() {
    return repository.getList();
  }
}
