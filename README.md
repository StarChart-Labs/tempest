# Tempest

[![Travis CI](https://img.shields.io/travis/com/StarChart-Labs/tempest.svg?branch=master)](https://travis-ci.com/StarChart-Labs/tempest) [![Black Duck Security Risk](https://copilot.blackducksoftware.com/github/repos/StarChart-Labs/tempest/branches/master/badge-risk.svg)](https://copilot.blackducksoftware.com/github/repos/StarChart-Labs/tempest/branches/master) 

Tempest is a web application for brainstorming new ideas with a group - allowing proposal, editing, acceptance, and rejection of aspects of any proposal

## Running

To run currently, add an "overrides.yml" to main.webapp/src/main/resources, with the following form:

```
security:
    oauth2:
        client:
            clientId: <Google Client ID>
            clientSecret: <Google Client Secret>
```

The client ID/secret can be generated as per the first steps of [these instructions](http://dba-presents.com/index.php/jvm/java/100-getting-started-with-google-sign-in-in-spring-boot-app)
