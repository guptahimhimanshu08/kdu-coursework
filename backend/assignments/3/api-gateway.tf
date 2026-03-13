resource "aws_api_gateway_rest_api" "counter_api" {
  name        = "himanshu-serverless-counter-rest-api"
  description = "REST API for serverless counter"
}

resource "aws_api_gateway_resource" "counter" {
  rest_api_id = aws_api_gateway_rest_api.counter_api.id
  parent_id   = aws_api_gateway_rest_api.counter_api.root_resource_id
  path_part   = "counter"
}

resource "aws_api_gateway_method" "get_counter" {
  rest_api_id   = aws_api_gateway_rest_api.counter_api.id
  resource_id   = aws_api_gateway_resource.counter.id
  http_method   = "GET"
  authorization = "NONE"
}

resource "aws_api_gateway_method" "put_counter" {
  rest_api_id   = aws_api_gateway_rest_api.counter_api.id
  resource_id   = aws_api_gateway_resource.counter.id
  http_method   = "PUT"
  authorization = "NONE"
}

resource "aws_api_gateway_integration" "get_integration" {
  rest_api_id = aws_api_gateway_rest_api.counter_api.id
  resource_id = aws_api_gateway_resource.counter.id
  http_method = aws_api_gateway_method.get_counter.http_method

  integration_http_method = "POST"
  type                    = "AWS_PROXY"
  uri                     = aws_lambda_function.counter.invoke_arn
}

resource "aws_api_gateway_integration" "put_integration" {
  rest_api_id = aws_api_gateway_rest_api.counter_api.id
  resource_id = aws_api_gateway_resource.counter.id
  http_method = aws_api_gateway_method.put_counter.http_method

  integration_http_method = "POST"
  type                    = "AWS_PROXY"
  uri                     = aws_lambda_function.counter.invoke_arn
}

resource "aws_api_gateway_method" "options_counter" {
  rest_api_id   = aws_api_gateway_rest_api.counter_api.id
  resource_id   = aws_api_gateway_resource.counter.id
  http_method   = "OPTIONS"
  authorization = "NONE"
}

resource "aws_api_gateway_integration" "options_integration" {
  rest_api_id = aws_api_gateway_rest_api.counter_api.id
  resource_id = aws_api_gateway_resource.counter.id
  http_method = aws_api_gateway_method.options_counter.http_method

  type = "MOCK"

  request_templates = {
    "application/json" = "{\"statusCode\": 200}"
  }
}

resource "aws_api_gateway_method_response" "options_response" {
  rest_api_id = aws_api_gateway_rest_api.counter_api.id
  resource_id = aws_api_gateway_resource.counter.id
  http_method = aws_api_gateway_method.options_counter.http_method
  status_code = "200"

  response_parameters = {
    "method.response.header.Access-Control-Allow-Headers" = true
    "method.response.header.Access-Control-Allow-Methods" = true
    "method.response.header.Access-Control-Allow-Origin"  = true
  }
}

resource "aws_api_gateway_integration_response" "options_integration_response" {
  rest_api_id = aws_api_gateway_rest_api.counter_api.id
  resource_id = aws_api_gateway_resource.counter.id
  http_method = aws_api_gateway_method.options_counter.http_method
  status_code = "200"

  response_parameters = {
    "method.response.header.Access-Control-Allow-Headers" = "'Content-Type'"
    "method.response.header.Access-Control-Allow-Methods" = "'GET,PUT,OPTIONS'"
    "method.response.header.Access-Control-Allow-Origin"  = "'*'"
  }

  depends_on = [
    aws_api_gateway_integration.options_integration,
    aws_api_gateway_method_response.options_response
  ]
}

resource "aws_api_gateway_deployment" "counter_deployment" {
  rest_api_id = aws_api_gateway_rest_api.counter_api.id

  depends_on = [
    aws_api_gateway_integration.get_integration,
    aws_api_gateway_integration.put_integration,
    aws_api_gateway_integration.options_integration
  ]
}

resource "aws_api_gateway_stage" "prod" {
  stage_name    = "prod"
  rest_api_id   = aws_api_gateway_rest_api.counter_api.id
  deployment_id = aws_api_gateway_deployment.counter_deployment.id
}

resource "aws_lambda_permission" "allow_rest_api" {
  statement_id  = "AllowExecutionFromAPIGateway"
  action        = "lambda:InvokeFunction"
  function_name = aws_lambda_function.counter.function_name
  principal     = "apigateway.amazonaws.com"
  source_arn    = "${aws_api_gateway_rest_api.counter_api.execution_arn}/*/*"
}