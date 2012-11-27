require 'rubygems'
require 'sinatra'
require 'json'

get("/tweets") do
  [
    {
      :status => 'howdy!',
      :screen_name => params["screen_name"]
    }
  ].to_json
end

post("/tweets") do
  p env
  [
    {
      :status => headers.to_s,
      :screen_name => params["screen_name"]
    }
  ].to_json
end
