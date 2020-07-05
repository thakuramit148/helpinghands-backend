echo 'Copying sql file...';
`docker cp ./data.sql mysql-service:/home/data.sql`
echo 'File copied successfully...\n\n';
echo 'Creating database...'
`docker exec -it -u root mysql-service bash -c 'mysql -e "CREATE DATABASE IF NOT EXISTS helping_hand;"'`
echo 'Database created successfully...\n\n';
echo 'Importing data to your database...';
`docker exec -it -u root mysql-service bash -c 'mysql helping_hand</home/data.sql'`
echo 'Data imported successfully...';
