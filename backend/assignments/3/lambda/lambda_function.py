import json
import os
import boto3

dynamodb = boto3.resource("dynamodb")
table = dynamodb.Table(os.environ["TABLE_NAME"])

CORS_HEADERS = {
    "Access-Control-Allow-Origin": "*",
    "Access-Control-Allow-Methods": "GET,PUT,OPTIONS",
    "Access-Control-Allow-Headers": "Content-Type"
}

def handler(event, context):
    try:
        method = event.get("httpMethod")

        if method == "OPTIONS":
            return {
                "statusCode": 200,
                "headers": CORS_HEADERS,
                "body": ""
            }

        if method == "GET":
            response = table.get_item(Key={"id": "counter"})
            count = response.get("Item", {}).get("count", 0)
            count = int(count)

            return {
                "statusCode": 200,
                "headers": CORS_HEADERS,
                "body": json.dumps({"count": count})
            }

        if method == "PUT":
            response = table.update_item(
                Key={"id": "counter"},
                UpdateExpression="SET #count = if_not_exists(#count, :zero) + :inc",
                ExpressionAttributeNames={"#count": "count"},
                ExpressionAttributeValues={":inc": 1, ":zero": 0},
                ReturnValues="UPDATED_NEW"
            )

            count = int(response["Attributes"]["count"])

            return {
                "statusCode": 200,
                "headers": CORS_HEADERS,
                "body": json.dumps({"count": count})
            }

        return {
            "statusCode": 405,
            "headers": CORS_HEADERS,
            "body": json.dumps({"message": "Method Not Allowed"})
        }

    except Exception as e:
        import traceback
        print(traceback.format_exc())
        return {
            "statusCode": 500,
            "headers": CORS_HEADERS,
            "body": json.dumps({"error": str(e)})
        }