# Run the website on your local machine

```bash
brew install rbenv # https://github.com/rbenv/rbenv
rbenv init

rbenv install
rbenv local
rbenv rehash

gem install bundler
bundle install
bundle exec jekyll serve -It
open http://localhost:4000/java-slack-sdk/
```

## Versioning

The Ruby version is contained in `.ruby-version` and should match GitHub Pages:
https://pages.github.com/versions/

If it is out of date, please update the version and send a PR with the changes.
