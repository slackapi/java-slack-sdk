require "sinatra"
require "rack"
require "rack/contrib"
require "json"
require "net/http"
require 'slack-ruby-client'

Slack.configure do |config|
  config.token = ENV['SLACK_API_TOKEN']
end

use Rack::PostBodyContentTypeParser

def send_webhook(url, message)
  uri = URI(url)
  http = Net::HTTP.new(uri.hostname, uri.port)
  http.use_ssl = true
  req = Net::HTTP::Post.new(uri.path, 'Content-Type': 'application/json')
  req.body = message.to_json
  webhook_resp = http.request(req)
  puts "code: #{webhook_resp.code} body: #{webhook_resp.body}"
end

get "/" do
  content_type 'application/json'
  # res = JSON.pretty_generate(request.env)
  # puts res
  {message: "Hello World"}.to_json
end

post "/" do
  content_type 'application/json'

  http_headers = request.env.select { |k, v| k.start_with?('HTTP_') }
  headers = http_headers.inject({}) do |a, (k, v)|
    a[k.sub(/^HTTP_/, "").downcase.gsub(/(^|_)\w/) { |word| word.upcase }.gsub("_", "-") ] = v
    a
  end
  puts headers.to_a.map { |v| v.join(": ") }.join("\n")
  puts request.body.read
  #puts "params: #{params}"

  if params[:challenge]
    {challenge: params[:challenge]}.to_json
  else
    ""
  end
end

