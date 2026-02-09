resource "aws_dynamodb_table" "counter" {
  name         = "himanshu-serverless-counter"
  billing_mode = "PAY_PER_REQUEST"

  hash_key = "id"

  attribute {
    name = "id"
    type = "S"
  }

  tags = {
    Name        = "himanshu-serverless-counter"
    Environment = "dev"
  }
}