public class LDAPAuth 
{
  private static String DOMAIN = "yours";
  private static String LDAP_HOST = "ldap://yours.com";
  private static String SEARCH_BASE = "dc=yours,dc=com";
  
  public static Map login(String user, String pass)
  {
    String returnedAtts[] ={ "sn", "givenName", "mail" };
    String searchFilter = "(&(objectClass=user)(sAMAccountName=" + user + "))";
    SearchControls searchCtls = new SearchControls();
    searchCtls.setReturningAttributes(returnedAtts);
    searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
    Hashtable env = new Hashtable();
    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
    env.put(Context.PROVIDER_URL, LDAP_HOST);
    env.put(Context.SECURITY_AUTHENTICATION, "simple");
    env.put(Context.SECURITY_PRINCIPAL, user + "@" + DOMAIN);
    env.put(Context.SECURITY_CREDENTIALS, pass); 
    LdapContext ctxGC = null;
    
    try {
      ctxGC = new InitialLdapContext(env, null);
      NamingEnumeration answer = ctxGC.search(SEARCH_BASE, searchFilter, searchCtls);
      while (answer.hasMoreElements()) {
        SearchResult sr = (SearchResult) answer.next();
        Attributes attrs = sr.getAttributes();
        Map attributesMap = null;
        if (attrs != null) {
          attributesMap = new HashMap();
          NamingEnumeration ne = attrs.getAll();
          while (ne.hasMore())
          {
            Attribute attr = (Attribute) ne.next();
            attributesMap.put(attr.getID(), attr.get());
          }
          ne.close();
        }
        return attributesMap;
      }
    }
    catch (NamingException ex)
    {
      throw new RuntimeException("Invalid username/password.");
    }    
    return null;
  }
  
  public static void main(String[] args) {
	LDAPAuth.login("username", "password");
  }
}