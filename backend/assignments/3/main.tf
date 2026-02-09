terraform{
    required_providers {
        aws = {
            source  = "hashicorp/aws"
            version = "~> 5.0"
        }
    }
}
provider "aws" {
  region = "ap-southeast-1"
}

resource "aws_s3_bucket" "himanshu-s3-bucket-tf-handson" {
  bucket = "himanshu-s3-bucket-tf-handson"
  tags = {
        "creator" = "Himanshu"
        "name"    = "himanshu-s3-bucket-TF-handson"
        "purpose" = "aws-hands-on-TF"
    }
}

resource "aws_s3_bucket_website_configuration" "himanshu-s3-bucket-tf-handson" {
  bucket = aws_s3_bucket.himanshu-s3-bucket-tf-handson.id

  index_document {
    suffix = "index.html"
  }

  error_document {
    key = "index.html"
  }
}

resource "aws_s3_bucket_public_access_block" "himanshu-s3-bucket-tf-handson" {
  bucket = aws_s3_bucket.himanshu-s3-bucket-tf-handson.id

  block_public_acls       = true
  ignore_public_acls      = true
  block_public_policy     = false
  restrict_public_buckets = false
}

resource "aws_s3_bucket_policy" "himanshu-s3-bucket-tf-handson" {
  bucket = aws_s3_bucket.himanshu-s3-bucket-tf-handson.id

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Sid       = "PublicReadForWebsite"
        Effect    = "Allow"
        Principal = "*"
        Action    = ["s3:GetObject"]
        Resource  = "${aws_s3_bucket.himanshu-s3-bucket-tf-handson.arn}/*"
      }
    ]
  })
}

resource "aws_s3_object" "index_html" {
  bucket = aws_s3_bucket.himanshu-s3-bucket-tf-handson.id
  key    = "index.html"
  source = "${path.module}/frontend/index.html"
  etag = filemd5("${path.module}/frontend/index.html")

  content_type = "text/html"
}