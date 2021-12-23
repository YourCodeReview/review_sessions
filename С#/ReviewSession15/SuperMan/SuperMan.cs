using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SuperMan : MonoBehaviour
{
    Vector3 placePlayer;
    [SerializeField] float _speed;
    [SerializeField] float upperBound;
    [SerializeField] float lowerBound;
    [SerializeField] float _power;
    [SerializeField] int _countLife;
    public int CountLife
    {
        get { return _countLife; }
    }

    [SerializeField] AudioSource _soundStrike;

    private void Start()
    {
        placePlayer = transform.position;
        Time.timeScale = 1;
    }
    private void FixedUpdate()
    {
        Move();
        ConditionOfLosing();
    }

    private void Move()
    {
        if (Input.GetKey(KeyCode.A) && placePlayer.x > lowerBound)
        {
            placePlayer.x -= _speed * Time.deltaTime;
            transform.position = placePlayer;
            placePlayer = transform.position;
            transform.LookAt(new Vector3(placePlayer.x - 1, placePlayer.y, placePlayer.z));
        }
        if (Input.GetKey(KeyCode.D) && placePlayer.x < upperBound)
        {
            placePlayer.x += _speed * Time.deltaTime;
            transform.position = placePlayer;
            placePlayer = transform.position;
            transform.LookAt(new Vector3(placePlayer.x + 1, placePlayer.y, placePlayer.z));
        }
        if (Input.GetKey(KeyCode.W) && placePlayer.z < upperBound)
        {
            placePlayer.z += _speed * Time.deltaTime;
            transform.position = placePlayer;
            placePlayer = transform.position;
            transform.LookAt(new Vector3(placePlayer.x, placePlayer.y, placePlayer.z + 1));
        }
        if (Input.GetKey(KeyCode.S) && placePlayer.z > lowerBound)
        {
            placePlayer.z -= _speed * Time.deltaTime;
            transform.position = placePlayer;
            placePlayer = transform.position;
            transform.LookAt(new Vector3(placePlayer.x, placePlayer.y, placePlayer.z - 1));
        }
    }

    private void OnCollisionEnter(Collision collision)
    {
        if (collision.gameObject.CompareTag("Enemy"))
        {
            collision.gameObject.GetComponent<Enemy>().GetStrike = true;
            Rigidbody targetRigid = collision.gameObject.GetComponent<Rigidbody>();
            Vector3 direction = collision.transform.position - transform.position;
            targetRigid.AddForce(direction * _power, ForceMode.Acceleration);
            _soundStrike.Play();
        }
        if (collision.gameObject.CompareTag("Ghost"))
        {
            Destroy(collision.gameObject);
            ReducedCountLifeSM();
        }
    }

    private void ConditionOfLosing()
    {
        if (_countLife <= 0)
        {
            Time.timeScale = 0;
            Destroy(gameObject);
        }
    } 

    public void ReducedCountLifeSM()
    {
        _countLife -= 1;
        GUI.ReducedNumberSignLife();
    }
}
