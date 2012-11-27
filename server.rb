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
