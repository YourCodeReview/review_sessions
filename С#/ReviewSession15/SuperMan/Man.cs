using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Man : MonoBehaviour
{
    [SerializeField] float _timeLive;
    private float _timer;
    private GameObject _gameObject;
    private GUI _score;
    private SuperMan _superMan;

    private void Start()
    {
        _gameObject = GetComponent<Man>().gameObject;
        _timer = _timeLive;
        _score = GameObject.FindGameObjectWithTag("UI").GetComponent<GUI>();
        _superMan = GameObject.FindGameObjectWithTag("SuperMan").GetComponent<SuperMan>();
    }

    private void Update()
    {
        _timer -= Time.deltaTime;
        if (_timer <= 0)
        {
            DestroyMan();
        }
    }

    private void DestroyMan()
    {
        Destroy(_gameObject);
        _score.Score += 100;
    }

    private void OnCollisionEnter(Collision collision)
    {
        if (collision.gameObject.CompareTag("Enemy"))
        {
            Destroy(_gameObject);
            _superMan.ReducedCountLifeSM();
        }
    }
}
