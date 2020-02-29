# Run the website on your local machine

```bash
brew install rbenv # https://github.com/rbenv/rbenv
rbenv init

rbenv install 2.6.5
rbenv local 2.6.5
rbenv rehash

gem install bundler
bundle install
bundle exec jekyll serve -It
open http://localhost:4000/java-slack-sdk/
```
