# spring-boot-keycloak-sample5

### Things todo list

1. Clone this repository: `git clone https://github.com/hendisantika/spring-boot-keycloak-sample5.git`
2. Navigate to the folder: `cd spring-boot-keycloak-sample5`
3. Run Keycloak Container: `docker run --name keycloak26 -p 8443:8443 -p 7080:7080 \
        -e KC_BOOTSTRAP_ADMIN_USERNAME=admin -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin \
        keycloak26 \
        start --optimized`
4. Run the application: `mvn clean spring-boot:run`
5. Import Postman Collection
6. Test using POSTMAN App
