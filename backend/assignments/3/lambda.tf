resource "aws_lambda_function" "counter" {
  function_name = "serverless-counter-lambda"

  runtime = "python3.11"
  handler = "lambda_function.handler"

  role = aws_iam_role.lambda_role.arn

  filename         = "${path.module}/lambda/function.zip"
  source_code_hash = filebase64sha256("${path.module}/lambda/function.zip")

  environment {
    variables = {
      TABLE_NAME = aws_dynamodb_table.counter.name
    }
  }
}