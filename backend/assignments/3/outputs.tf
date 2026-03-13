output "s3-bucket-name" {
  description = "Name of the S3 bucket hosting the frontend"
  value = aws_s3_bucket.himanshu-s3-bucket-tf-handson.bucket
}

output "s3_website_url" {
  description = "Public URL of the S3 static website"
  value       = aws_s3_bucket_website_configuration.himanshu-s3-bucket-tf-handson.website_endpoint
}

output "rest_api_invoke_url" {
  description = "Invoke URL for the REST API"
  value = "https://${aws_api_gateway_rest_api.counter_api.id}.execute-api.${var.aws_region}.amazonaws.com/${aws_api_gateway_stage.prod.stage_name}"
}