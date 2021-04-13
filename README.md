# example-metosin-service

An example Clojure service using some [Metosin libraries](https://github.com/metosin).

* HTTP server: [aleph](https://github.com/clj-commons/aleph)
* HTTP encoding/decoding/format negotiation: [muuntaja](https://github.com/metosin/muuntaja)
* Routing: [reitit](https://github.com/metosin/reitit)
* JSON encoding/decoding: [jsonista](https://github.com/metosin/jsonista)
* URL generation: [hype](https://github.com/b-social/hype)
* Component framework: [integrant](https://github.com/weavejester/integrant)
* Persistence: [crux](https://github.com/juxt/crux)

## Learnings:

1. Using `hype` for URL generation is possible by modifying the expander function, which allows you to define the routes
   in the bidi-style route structure that `hype` expects. EDIT: more complicated use cases do not seem possible due to
   differences in how route parameters are defined
2. Integrant serves to create components that have a lifecycle. It allows you to define
configuration for these components, but does not give capabilities out of the box to load
   configuration from various sources (environment, yaml/json file). I.e, it is not a replacement
   for [configurati](https://github.com/logicblocks/configurati)