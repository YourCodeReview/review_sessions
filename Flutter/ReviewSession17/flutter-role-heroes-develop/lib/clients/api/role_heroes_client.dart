import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:meta/meta.dart';
import 'package:role_heroes/clients/api/client.dart';
import 'package:role_heroes/clients/api/exceptions/server_error.dart';
import 'package:role_heroes/utils/secure_storages.dart';

class RoleHeroesClient implements Client {
  final AccessTokenStorage accessTokenStorage;
  final Dio dio;

  RoleHeroesClient({
    @required this.accessTokenStorage,
    @required this.dio,
  });

  Future _request(String method, String url, {Map data, String token}) async {
    final headers = Map<String, dynamic>();

    if (token != null) {
      headers['Authorization'] = 'Bearer ' + token;
    }

    final Options options = Options(
      method: method,
      headers: headers,
    );

    try {
      // TODO add query for GET requests
      final Response response = await this.dio.request(
        url,
        data: jsonEncode(data),
        options: options,
      );

      return response.data;
    } on DioError catch (error) {
      throw new ServerError(data: error.response.data);
    }
  }

  @override
  Future<List> games(String token) async {
    return await this._request('GET', '/games', token: token);
  }

  @override
  Future login(Map data) async {
    return await this._request('POST', '/auth/login', data: data);
  }

  @override
  Future logout(String token) async {
    return await this._request('GET', '/logout', token: token);
  }

  @override
  Future refresh(String token) async {
    return await this._request('GET', '/auth/refresh', token: token);
  }

  @override
  Future register(Map data) async {
    return await this._request('POST', '/auth/register', data: data);
  }

  @override
  Future updateHero(String token, int heroId, Map data) async {
    return await this._request('PUT', '/heroes/$heroId', token: token);
  }

  @override
  Future<Map> userHero(String token, int heroId) async {
    return await this._request('GET', '/heroes/$heroId', token: token);
  }

  @override
  Future<List> userHeroes(String token, int gameId) async {
    return await this._request('GET', '/games/$gameId/heroes', token: token);
  }

  @override
  Future createHero(String token, Map data) async {
    return await this._request('POST', '/heroes/create', data: data, token: token);
  }

  @override
  Future deleteHero(String token, int heroId) async {
    return await this._request('DELETE', '/heroes/$heroId', token: token);
  }

  @override
  Future updateHeroCharacteristicValue(String token, int heroId, int characteristicId, Map<String, dynamic> data) async {
    return await this._request(
      'PUT',
      '/heroes/$heroId/characteristics/$characteristicId/value',
      token: token,
      data: data,
    );
  }

  @override
  Future updateHeroAttributeValue(String token, int heroId, int attributeId, Map<String, dynamic> data) async {
    return await this._request(
      'PUT',
      '/heroes/$heroId/attributes/$attributeId/value',
      token: token,
      data: data,
    );
  }

  @override
  Future<List> getStructureAttributeValues(String token, int attributeId) async {
    return await this._request(
      'GET',
      '/structural_attributes/$attributeId/values',
      token: token,
    );
  }

  @override
  Future setStructuralAttributeValues(String token, int heroId, int attributeId, List<int> valueIds) async {
    return await this._request(
      'PUT',
      '/heroes/$heroId/structural_attributes/$attributeId/value',
      token: token,
      data: {
        'value_ids': valueIds,
      },
    );
  }
}
