import 'package:role_heroes/setup/get_it/builders.dart';
import 'package:role_heroes/setup/get_it/client.dart';
import 'package:role_heroes/setup/get_it/controllers.dart';
import 'package:role_heroes/setup/get_it/repositories.dart';
import 'package:role_heroes/setup/get_it/screens.dart';

setup() {
  setupClient();
  setupRepositories();
  setupControllers();
  setupBuilders();
  setupScreens();
}
