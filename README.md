# Return Management System

## How to Run
### Gradle
```shell
./gradlew bootRun
```

## API(s)
### Create token for pending returns
```shell
curl --request POST \
  --url http://localhost:8080/pending/returns \
  --header 'Content-Type: application/json' \
  --data '{
	"orderId": "RK-478",
	"email": "john@example.com"
}'
```

### Create Return Request
#### Partial Return (1/2 item from an order)
```shell
curl --request POST \
  --url http://localhost:8080/returns \
  --header 'Content-Type: application/json' \
  --data '{
	"token": "JDJhJDEwJE9HN3FVQlhLQi9lRGJrLzBjVGFTMS5NTTkyb3NhV203ZERFMlJyajBCWFk4dHpoZU0wNXpP",
	"items": [
		{
			"sku": "MENS-156",
			"quantity": 2
		}
	]
}'
```
#### Partial Return (partial quantity of a single item from an order)
```shell
curl --request POST \
  --url http://localhost:8080/returns \
  --header 'Content-Type: application/json' \
  --data '{
	"token": "JDJhJDEwJE9HN3FVQlhLQi9lRGJrLzBjVGFTMS5NTTkyb3NhV203ZERFMlJyajBCWFk4dHpoZU0wNXpP",
	"items": [
		{
			"sku": "MENS-156",
			"quantity": 1
		}
	]
}'
```
#### Full Return (All item with full quantity of a single order)
```shell
curl --request POST \
  --url http://localhost:8080/returns \
  --header 'Content-Type: application/json' \
  --data '{
	"token": "JDJhJDEwJE9HN3FVQlhLQi9lRGJrLzBjVGFTMS5NTTkyb3NhV203ZERFMlJyajBCWFk4dHpoZU0wNXpP",
	"items": [
		{
			"sku": "MENS-156",
			"quantity": 2
		},
		{
			"sku": "NIKE-7",
			"quantity": 1
		}
	]
}'
```

### Get Return Data
```shell
curl --request GET \
  --url http://localhost:8080/returns/12
```

### QC-ing Returned Item Data
#### Approve returned item
```shell
curl --request PUT \
  --url http://localhost:8080/returns/12/items/13/qc/status \
  --header 'Accept: application/json' \
  --header 'Content-Type: application/json' \
  --data '{
	"status": "APPROVED"
}'
```

#### Reject returned item
```shell
curl --request PUT \
  --url http://localhost:8080/returns/12/items/13/qc/status \
  --header 'Accept: application/json' \
  --header 'Content-Type: application/json' \
  --data '{
	"status": "REJECTED"
}'
```