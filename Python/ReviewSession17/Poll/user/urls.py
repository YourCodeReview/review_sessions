from rest_framework import routers

from user.views import UserViewSet

router = routers.SimpleRouter()
router.register(r'users', UserViewSet)

urlpatterns = router.urls
