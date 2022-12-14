package dev.ranjan.androidnewlearnings.designpatterns.creational_patterns

// 1. This is a basic interface for all the hosting plans.
interface HostingPackageInterface {
    fun getServices(): List<String>
}

// 2. This enum specifies all the hosting package types.
enum class HostingPackageType {
    STANDARD, PREMIUM
}

// 3. `StandardHostingPackage` conforms to the interface and implements the required method to list all the services.
class StandardHostingPackage : HostingPackageInterface {
    override fun getServices(): List<String> {
        return listOf("Standard 1", "Standard 2")
    }
}

// 4. `PremiumHostingPackage` confirms to the interface and implements the required method to list all the services.
class PremiumHostingPackage : HostingPackageInterface {
    override fun getServices(): List<String> {
        return listOf("Premium 1", "Premium 2")
    }
}

// 5. HostingPackageFactory is a singleton class with a helper method.
object HostingPackageFactory {
    // 6. getHostingFrom inside HostingPackageFactory is responsible for creating all the objects.
    fun getHostingFrom(type: HostingPackageType): HostingPackageInterface {
        return when (type) {
            HostingPackageType.STANDARD -> {
                StandardHostingPackage()
            }
            HostingPackageType.PREMIUM -> {
                PremiumHostingPackage()
            }
        }
    }
}


fun main() {
    val standardData = HostingPackageFactory.getHostingFrom(HostingPackageType.STANDARD)
    val premiumData = HostingPackageFactory.getHostingFrom(HostingPackageType.PREMIUM)
}
