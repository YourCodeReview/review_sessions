using UnityEngine;

public class SoundExplosion : MonoBehaviour
{
    private float _timeSound;

    private void Start()
    {
        _timeSound = gameObject.GetComponent<AudioSource>().clip.length;
    }

    private void Update()
    {
        Destroy(gameObject, _timeSound);
    }
}
