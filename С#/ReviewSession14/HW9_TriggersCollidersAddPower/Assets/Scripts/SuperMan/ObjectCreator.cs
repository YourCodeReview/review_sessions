using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class ObjectCreator : MonoBehaviour
{
    [SerializeField] GameObject _enemy;
    [SerializeField] GameObject _man;
    [SerializeField] GameObject _ghost;

    [SerializeField] float _enemyInterval;
    private float _enemyTimer;
    [SerializeField] float _manInterval;
    private float _manTimer;
    [SerializeField] float _ghostInterval;
    private float _ghostTimer;

    [SerializeField] float _coreBoundCreate;

    System.Random rnd = new System.Random();
    private float pointY;


    void Start()
    {
        pointY = transform.position.y - (float)93;
        _manTimer = _manInterval;
        _enemyTimer = _enemyInterval;
        _ghostTimer = _ghostInterval;
    }

    void Update()
    {
        _manTimer -= Time.deltaTime;
        if (_manTimer <= 0)
        {
            CreateItem(_man);
            _manTimer = _manInterval;
        }
        _enemyTimer -= Time.deltaTime;
        if (_enemyTimer <= 0)
        {
            CreateItem(_enemy);
            _enemyTimer = _enemyInterval;
        }
        _ghostTimer -= Time.deltaTime;
        if (_ghostTimer <= 0)
        {
            CreateItem(_ghost);
            _ghostTimer = _ghostInterval;
        }
    }

    private Vector3 PointCreation()
    {
        int limitBound = 2 * (int)(_coreBoundCreate * 10) ;
        float deductibleX = rnd.Next(limitBound) / 10;
        float deductibleZ = rnd.Next(limitBound) / 10;
        Vector3 pointCreate = new Vector3(_coreBoundCreate - deductibleX, pointY, _coreBoundCreate - deductibleZ);
        return pointCreate;
    }

    private void CreateItem(GameObject prefab)
    {
        GameObject item = Instantiate<GameObject>(prefab);
        item.transform.position = PointCreation();
    }



}
