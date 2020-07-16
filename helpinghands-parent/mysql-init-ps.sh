echo 'Setting password...'
`mysql -e "SET PASSWORD FOR 'root'@'localhost' = PASSWORD('$DB_PASSWORD');"`
echo 'Password successfully assigned!...\n\n';
echo 'Creating database...'
`mysql -e "CREATE DATABASE IF NOT EXISTS helping_hand;"`
echo 'Database created successfully...\n\n';
echo 'Importing data to your database...';
`mysql helping_hand</home/data.sql`
echo 'Data imported successfully...';
