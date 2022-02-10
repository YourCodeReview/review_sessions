from django.contrib import admin
from django.urls import path, include
from drf_yasg import openapi
from drf_yasg.views import get_schema_view
from rest_framework import permissions
from rest_framework.authtoken import views


schema_view = get_schema_view(
   openapi.Info(
      title="Poll API",
      default_version='v1',
      description="Poll Api documentation",
      contact=openapi.Contact(email='minovaziz@gmail.com'),
   ),
   public=True,
   permission_classes=(permissions.AllowAny,),
)


urlpatterns = [
    path('admin/', admin.site.urls),
    path('api/', include('poll.urls')),
    path('api-auth/', include('rest_framework.urls', namespace='rest_framework')),
    path('user/', include('user.urls')),
    path('api-token-auth/', views.obtain_auth_token),
    path('docs/', schema_view.with_ui('swagger', cache_timeout=0), name='schema-swagger-ui'),

]
