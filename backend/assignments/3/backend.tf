terraform {
  backend "s3" {
    bucket         = "himanshu-terraform-state-bucket-2"
    key            = "serverless-counter/terraform.tfstate"
    region         = "ap-southeast-1"

    dynamodb_table = "terraform-locks"
    encrypt        = true
  }
}