using UnityEngine;

public class Bomb : MonoBehaviour
{
    [SerializeField] private float _timeToExplosion;
    private float _timer;
    [SerializeField] private float _radiusExplosion;
    [SerializeField] private float _powerExplosion;
    private Rigidbody[] _arraytGameObject;
    private GameObject _hatch;
    [SerializeField] private GameObject _soundExplosion;


    private void Start()
    {
        _timer = _timeToExplosion;
        _hatch = GameObject.FindGameObjectWithTag("Hatch");
    }

    private void Update()
    {
        _timer -= Time.deltaTime;
        if (_timer < 0)
        {
            Explosion();         
        }
    }

    private void Explosion()
    {
        _arraytGameObject = FindObjectsOfType<Rigidbody>();
        foreach (Rigidbody item in _arraytGameObject)
        {
            Vector3 target = item.gameObject.transform.position;
            float distanceToTarget = Vector3.Distance(target, transform.position);
            if (distanceToTarget < _radiusExplosion)
            {
                Vector3 direction = target - transform.position;
                float explosivePower = (Vector3.Distance(target, transform.position)) / _radiusExplosion;
                item.AddForce(direction.normalized * explosivePower * _powerExplosion, ForceMode.Impulse);
            }                
        }
        SoundExplosion();
        _hatch.GetComponent<DropBomb>().HaveBomb = false;
        Destroy(gameObject);
    }

    private void SoundExplosion()
    {
        Instantiate(_soundExplosion);
        _soundExplosion.transform.position = transform.position;
    }
}
