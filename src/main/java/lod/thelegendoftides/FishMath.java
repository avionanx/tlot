package lod.thelegendoftides;

import legend.core.MathHelper;
import org.joml.Vector3f;

public class FishMath {
  public static boolean rayTriangleIntersect(final Vector3f orig, final Vector3f dir, final Vector3f v0, final Vector3f v1, final Vector3f v2, float t) {
    // Compute the plane's normal
    Vector3f v0v1 = new Vector3f(v1).sub(v0);
    Vector3f v0v2 = new Vector3f(v2).sub(v0);
    // No need to normalize
    Vector3f N = new Vector3f(v0v1).cross(v0v2); // N

    // Step 1: Finding P

    // Check if the ray and plane are parallel
    float NdotRayDirection = N.dot(dir);
    if(MathHelper.flEq(NdotRayDirection, 0)) { // Almost 0
      return false; // They are parallel, so they don't intersect!
    }

    // Compute d parameter using equation 2
    float d = -N.dot(v0);

    // Compute t (equation 3)
    t = -(N.dot(orig) + d) / NdotRayDirection;

    // Check if the triangle is behind the ray
    if(t < 0 || t > 1) {
      return false; // The triangle is behind
    }

    // Compute the intersection point using equation 1
    Vector3f P = new Vector3f(dir).mul(t).add(orig);

    // Step 2: Inside-Outside Test
    Vector3f Ne; // Vector perpendicular to triangle's plane

    // Test sidedness of P w.r.t. edge v0v1
    Vector3f v0p = new Vector3f(P).sub(v0);
    Ne = new Vector3f(v0v1).cross(v0p);
    if(N.dot(Ne) < 0) {
      return false; // P is on the right side
    }

    // Test sidedness of P w.r.t. edge v2v1
    Vector3f v2v1 = new Vector3f(v2).sub(v1);
    Vector3f v1p = new Vector3f(P).sub(v1);
    Ne = new Vector3f(v2v1).cross(v1p);
    if(N.dot(Ne) < 0) {
      return false; // P is on the right side
    }

    // Test sidedness of P w.r.t. edge v2v0
    Vector3f v2v0 = new Vector3f(v0).sub(v2);
    Vector3f v2p = new Vector3f(P).sub(v2);
    Ne = new Vector3f(v2v0).cross(v2p);
    if(N.dot(Ne) < 0) {
      return false; // P is on the right side
    }

    return true; // The ray hits the triangle
  }
}
