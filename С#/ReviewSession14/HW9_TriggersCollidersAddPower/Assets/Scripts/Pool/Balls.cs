using UnityEngine;

public class Balls : MonoBehaviour
{
    private AudioSource _soundStrike;
    private float _minHieghtBall = -10;

    private void Start()
    {
        _soundStrike = gameObject.GetComponent<AudioSource>();
    }

    private void Update()
    {
        if (transform.position.y <= _minHieghtBall)
        {
            Destroy(gameObject);
        }
    }

    private void OnCollisionExit(Collision collision)
    {
        if (collision.gameObject.CompareTag("WhiteBall") || collision.gameObject.CompareTag("BlackBall"))
        {
            _soundStrike.Play();
        }
    }
}
